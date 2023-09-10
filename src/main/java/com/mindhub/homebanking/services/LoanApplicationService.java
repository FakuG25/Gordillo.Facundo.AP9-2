package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface LoanApplicationService {
    ResponseEntity<List<LoanDTO>> getLoans();

    LoanDTO getLoansId(@PathVariable Long id);

}
