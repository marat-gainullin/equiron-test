package com.equiron.endpoints;

import com.equiron.exceptions.NoItemException;
import com.equiron.exceptions.SameCounterpartyException;
import com.equiron.exceptions.BadValueFormatException;
import com.equiron.exceptions.ValueMissingException;
import com.equiron.model.Deal;
import com.equiron.store.Deals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Deals Json REST end point.
 *
 * @author mg
 */
@RestController
public class DealsEndpoint {

    /**
     * Deals store.
     */
    @Autowired
    private Deals deals;

    /**
     * Creates a new deal.
     * <p>
     * Sets the 'Location' header of response with an uri
     * the created deal can found at.
     * </p>
     *
     * @param deal     {@link Deal} read from client.
     * @param response {@link HttpServletResponse} instance used to process
     *                 current client's request.
     */
    @RequestMapping(
            path = "/deals",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public final void addDeal(@RequestBody final Deal deal,
                              final HttpServletResponse response) {
        response.setHeader("Location", "/deals/" + deals.add(deal));
    }

    /**
     * Looks up a deal by its key.
     *
     * @param key Key of a deal to lookup.
     * @return An {@code Deal} instance got from repository.
     * @throws NoItemException if no deal with key found.
     */
    @RequestMapping(path = "/deals/{deal-key}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public final Deal getDeal(@PathVariable("deal-key") final Long key)
            throws NoItemException {
        return deals.find(key).orElseThrow(
                () -> new NoItemException("/deals/" + key)
        );
    }

    /**
     * Handles {@code NoItemException} exceptions.
     *
     * @param ex A {@code NoItemException} instance.
     * @return A {@code String} to be bound as a graceful http response.
     * @see NoItemException
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoItemException.class)
    public final String handleException(final NoItemException ex) {
        return ex.getMessage();
    }

    /**
     * Handles {@code ValueMissingException} exceptions.
     *
     * @param ex A {@code ValueMissingException} instance.
     * @return A {@code String} to be bound as a graceful http response.
     * @see NoItemException
     */
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(ValueMissingException.class)
    public final String handleException(final ValueMissingException ex) {
        return ex.getMessage();
    }

    /**
     * Handles {@code SameCounterpartyException} exceptions.
     *
     * @param ex A {@code SameCounterpartyException} instance.
     * @return A {@code String} to be bound as a graceful http response.
     * @see NoItemException
     */
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(SameCounterpartyException.class)
    public final String handleException(final SameCounterpartyException ex) {
        return ex.getMessage();
    }

    /**
     * Handles {@code BadValueFormatException} exceptions.
     *
     * @param ex A {@code BadValueFormatException} instance.
     * @return A {@code String} to be bound as a graceful http response.
     * @see NoItemException
     */
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(BadValueFormatException.class)
    public final String handleException(final BadValueFormatException ex) {
        return ex.getMessage();
    }
}
