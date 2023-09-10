package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class LoanApplicationServiceImplement implements LoanApplicationService {
    @Autowired
    LoanRepository loanRepository;

    @Override
    public ResponseEntity<List<LoanDTO>> getLoans() {
        List<LoanDTO> loanDTOs = loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(loanDTOs, HttpStatus.OK);
    }

    @Override
    public LoanDTO getLoansId(Long id) {
        return new LoanDTO(loanRepository.findById(id).orElse(null));
    }
}
