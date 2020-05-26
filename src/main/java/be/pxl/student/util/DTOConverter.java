package be.pxl.student.util;

import be.pxl.student.entity.Payment;
import be.pxl.student.rest.DTO.PaymentDTO;

import java.util.ArrayList;
import java.util.List;

public class DTOConverter {
    public static List<PaymentDTO> convertPaymentsToPaymentDTO(List<Payment> payments) {
        List<PaymentDTO> dtos = new ArrayList<>();
        for (Payment p : payments) {
            dtos.add(new PaymentDTO(p.getIBAN(), p.getDate(),
                    p.getAmount(), p.getCurrency(), p.getDetail(), p.getCounterAccount().getIBAN()));
        }
        return dtos;
    }
}
