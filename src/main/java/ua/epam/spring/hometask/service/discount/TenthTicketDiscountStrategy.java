package ua.epam.spring.hometask.service.discount;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class TenthTicketDiscountStrategy implements DiscountStrategy {
    @Override
    public byte getDiscount(@Nullable User user,
                            @Nonnull Event event,
                            @Nonnull LocalDateTime airDateTime,
                            long numberOfTickets) {

        if (user != null) {
            if (((user.getTickets().size() + numberOfTickets) % 10) - (user.getTickets().size() % 10) > 0) {
                return 50;
            }
        } else {
            if (numberOfTickets % 10 > 0)
                return 50;
        }

        return 0;
    }
}
