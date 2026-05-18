package ru.psu.coursework.server.network;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LobbyManager {

    private final List<PlayerConnection> players = new CopyOnWriteArrayList<>();
    private final Map<String, Room> activeRooms = new ConcurrentHashMap<String, Room>();

    public synchronized void addPlayer(PlayerConnection player) {
        players.add(player);
    }

    public synchronized void createRoom(PlayerConnection creator, String roomCode) {
        String code = roomCode.trim().toUpperCase();

        if (activeRooms.containsKey(code)) {
            creator.send(new Message(Commands.ERROR, "The room code is already taken! Please create another one!!!"));

            return;
        }

        Room newRoom = new Room(code, creator);

        activeRooms.put(code, newRoom);
        players.remove(creator);

        creator.send(new Message(Commands.CREATE_ROOM, "The room " + code + " has been successfully created. Waiting for the opponent..."));
        System.out.println("Created the room " + code);
    }

    public synchronized void joinRoom(PlayerConnection guest, String roomCode) {
        String code = roomCode.trim().toUpperCase();
        Room room = activeRooms.get(code);

        if (room == null) {
            guest.send(new Message(Commands.ERROR, "The room " + code + " does not exist!"));

            return;
        }

        if (room.isFull()) {
            guest.send(new Message(Commands.ERROR, "The room " + code + " is already full!"));

            return;
        }

        room.addOpponent(guest);
        players.remove(guest);

        System.out.println("Player " + guest.getUsername() + " joined the room successfully!");

        room.start();
    }


    public synchronized void removePlayer(PlayerConnection player) {
        players.remove(player);
    }
}
