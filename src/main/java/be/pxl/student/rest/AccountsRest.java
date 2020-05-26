package be.pxl.student.rest;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.jpa.AccountJPA;
import be.pxl.student.rest.DTO.PaymentDTO;
import be.pxl.student.rest.DTO.PaymentResource;
import be.pxl.student.util.DTOConverter;
import be.pxl.student.util.EntityManagerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Path("accounts")
public class AccountsRest {
    private static Logger logger = LogManager.getLogger(AccountsRest.class);

    @GET
    @Path("{accountName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountByName(@PathParam("accountName") String name) {
        try {
            EntityManager em = EntityManagerUtil.createEntityManager();
            AccountJPA accountJPA = new AccountJPA(em);
            List<Payment> payments = accountJPA.getPaymentsByAccountName(name);
            List<PaymentDTO> formattedPayments = DTOConverter.convertPaymentsToPaymentDTO(payments);
            return Response.ok(formattedPayments).build();
        } catch (NoResultException ex) {
            return Response.status(404).build();
        }
    }

    @POST
    @Path("{accountName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPaymentToAccount(@PathParam("accountName") String name, PaymentResource pr) {
        try {
            EntityManager em = EntityManagerUtil.createEntityManager();
            AccountJPA accountJPA = new AccountJPA(em);
            Account accountToUpdate = accountJPA.getByName(name);
            Payment newPayment = new Payment(accountToUpdate.getIBAN(), Date.from(Instant.now()), pr.amount, pr.currency, "");
            newPayment.setAccount(accountToUpdate);
            try {
                newPayment.setCounterAccount(accountJPA.getByIBAN(pr.counterIBAN));
            } catch (NoResultException ex) {
                newPayment.setCounterAccount(accountJPA.create(new Account(pr.counterIBAN)));
            }
            accountToUpdate.getPayments().add(newPayment);
            accountJPA.update(accountToUpdate);
            return Response.created(UriBuilder.fromPath("/api/accounts/" + accountToUpdate.getId()).build()).build();
        } catch (NoResultException ex) {
            return Response.status(404).build();
        }
    }
}