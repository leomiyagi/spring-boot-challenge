package com.hackerrank.stocktrades.controller;

import com.hackerrank.stocktrades.exception.StockTradeNotFoundException;
import com.hackerrank.stocktrades.model.StockTrade;
import com.hackerrank.stocktrades.repository.StockTradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class StockTradeRestController {

    @Autowired
    private StockTradeRepository stockTradeRepository;

    @PostMapping("/trades")
    public ResponseEntity<StockTrade> createTrade(@Valid @RequestBody StockTrade stockTrade) {
        StockTrade save = stockTradeRepository.save(stockTrade);

        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @GetMapping("/trades")
    public List<StockTrade> getTrades(@RequestParam(required = false) String type, @RequestParam(required = false) Integer userId) {

        if (type != null && userId != null) {
            return stockTradeRepository.findStockTradesByTypeAndUserId(type, userId);
        }

        if (type != null) {
            return stockTradeRepository.findStockTradesByType(type);
        }

        if (userId != null) {
            return stockTradeRepository.findStockTradesByUserId(userId);
        }

        return stockTradeRepository.findAll();
    }

    @GetMapping("/trades/{id}")
    public StockTrade getTrade(@PathVariable("id") Integer id) throws StockTradeNotFoundException {
        Optional<StockTrade> optionalStockTrade = stockTradeRepository.findById(id);

        return optionalStockTrade.orElseThrow(StockTradeNotFoundException::new);
    }
    
}