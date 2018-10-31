package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.accounts.Card;
import org.a2lpo.bank.notownbank.payload.ApiResponse;
import org.a2lpo.bank.notownbank.payload.OpenCardRequest;
import org.a2lpo.bank.notownbank.repos.AccountRepo;
import org.a2lpo.bank.notownbank.repos.CardsRepo;
import org.a2lpo.bank.notownbank.repos.ClientRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.CardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO Сделать котроллер с картами
 */
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardsRepo cardsRepo;
    private final ClientRepo clientRepo;
    private final CardService cardService;
    private final AccountRepo accountRepo;

    public CardController(CardsRepo cardsRepo,
                          ClientRepo clientRepo,
                          CardService cardService,
                          AccountRepo accountRepo) {
        this.cardsRepo = cardsRepo;
        this.clientRepo = clientRepo;
        this.cardService = cardService;
        this.accountRepo = accountRepo;
    }

    @PostMapping
    public ApiResponse createCard(@CurrentUser UserPrincipal userPrincipal,
                                  @RequestBody OpenCardRequest openCardRequest) {
        Card card = new Card(accountRepo.findPersonalAccountByUserIdAndByAccountNumber(userPrincipal.getId(), openCardRequest.getAccountNumber()).get());
        cardsRepo.save(card);
        return new ApiResponse(false, card.toString());
    }
}
