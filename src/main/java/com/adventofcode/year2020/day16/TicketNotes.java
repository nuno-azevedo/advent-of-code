package com.adventofcode.year2020.day16;

import java.util.List;

record TicketNotes(List<TicketRule> rules, Ticket myTicket, List<Ticket> nearbyTickets) { }
