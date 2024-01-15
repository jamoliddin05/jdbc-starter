package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.dao.TicketDao;
import com.dmdev.jdbc.starter.dto.TicketFilter;
import com.dmdev.jdbc.starter.entity.Ticket;
import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        var ticketFilter = new TicketFilter(3, 0, "Евгений Кудрявцев", "A1");
        var tickets = TicketDao.getInstance().findAll(ticketFilter);
        tickets.forEach(System.out::println);
    }

    private static void deleteTest(Long id) {
        var ticketDao = TicketDao.getInstance();
        var deleteResult = ticketDao.delete(id);
        System.out.println(deleteResult);
    }

    private static void saveTest() {
        var ticketDao = TicketDao.getInstance();
        var ticket = new Ticket();
        ticket.setPassengerNo("1234567");
        ticket.setPassengerName("Test");
        ticket.setFlightId(3L);
        ticket.setSeatNo("B3");
        ticket.setCost(BigDecimal.TEN);

        var savedTicket = ticketDao.save(ticket);
        System.out.println(savedTicket);
    }

    private static void selectScript() throws SQLException {
        String sql = """
                    SELECT *
                    FROM ticket
                """;

        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getLong("id"));
                System.out.println(resultSet.getString("passenger_no"));
                System.out.println(resultSet.getString("passenger_name"));
                System.out.println("----------");
            }
        }
    }
}
