package com.equiron.endpoints;

import com.equiron.exceptions.NoItemException;
import com.equiron.model.Deal;
import com.equiron.store.Deals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
    public final void addDeal(@Valid @RequestBody final Deal deal,
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
     * Handles {@link NoItemException} exception.
     *
     * @param ex A {@link NoItemException} instance.
     * @return A {@link ErrorDescription} to be bound as a graceful
     * rest json response.
     * @see NoItemException
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoItemException.class)
    public final
    @ResponseBody
    ErrorDescription handleNoItem(final NoItemException ex) {
        return new ErrorDescription(HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
    }

    /**
     * Handles {@link HttpMessageConversionException} and
     * {@link MethodArgumentNotValidException} exceptions.
     *
     * @param ex An {@link Exception} instance.
     * @return A {@link ErrorDescription} to be bound as a graceful
     * rest json response.
     * @see HttpMessageConversionException, MethodArgumentNotValidException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageConversionException.class,
            MethodArgumentNotValidException.class})
    public final
    @ResponseBody
    ErrorDescription handleBind(final Exception ex) {
        return new ErrorDescription(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
    }
}
