package io.upschool.service;

import io.upschool.Validation.CardValidation;
import io.upschool.Validation.EmailValidation;
import io.upschool.Validation.PhoneValidation;
import io.upschool.dto.request.AirlineCompanySaveRequest;
import io.upschool.dto.response.AirlineCompanySaveResponse;
import io.upschool.dto.response.AirportSaveResponse;
import io.upschool.entity.AirlineCompany;
import io.upschool.exception.BusinessException;
import io.upschool.repository.AirlineCompanyRepository;
import io.upschool.service.Interface.AirlineCompanyService;
import io.upschool.utils.AirlineSystemConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AirlineCompanyServiceImpl implements AirlineCompanyService {
    private final AirlineCompanyRepository airlineCompanyRepository;

    @Override
    public List<AirlineCompanySaveResponse> getAll() {
        var allAirlineCompany = airlineCompanyRepository.findAll();
        List<AirlineCompanySaveResponse> list = allAirlineCompany.stream().map(airlineCompany -> getAirlineCompanySaveResponse(airlineCompany)).toList();
        return list;
    }

    @Transactional
    @Override
    public AirlineCompanySaveResponse save(AirlineCompanySaveRequest request) {
        CheckValidations(request);
        AirlineCompany airport = getAirport(request);
        AirlineCompany savedAirport = airlineCompanyRepository.save(airport);
        return getAirlineCompanySaveResponse(savedAirport);
    }

    private static void CheckValidations(AirlineCompanySaveRequest request) {
        if (EmailValidation.IsNotValid(request.getEmail()))
            throw new BusinessException(AirlineSystemConstant.INVALID_EMAIL_EXCEPTION);
    }

    private static AirlineCompany getAirport(AirlineCompanySaveRequest request) {
        return AirlineCompany
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }

    @Override
    public List<AirlineCompanySaveResponse> searchAirlineCompanysByName(String name) {
        var filteredAirlineCompany = airlineCompanyRepository.findAirlineCompaniesByNameContainingIgnoreCase(name);
        List<AirlineCompanySaveResponse> list = filteredAirlineCompany.stream().map(airlineCompany -> getAirlineCompanySaveResponse(airlineCompany)).toList();
        return list;
    }

    private static AirlineCompanySaveResponse getAirlineCompanySaveResponse(AirlineCompany airlineCompany) {
        return AirlineCompanySaveResponse.builder()
                .name(airlineCompany.getName())
                .email(airlineCompany.getEmail())
                .build();
    }

    @Override
    public AirlineCompany getReferenceById(Long id) {
        return airlineCompanyRepository.getReferenceById(id);
    }

    public void deleteById(Long id) {
        var airlineCompany = airlineCompanyRepository.findById(id).orElseThrow(() -> new BusinessException(AirlineSystemConstant.DATA_NOT_FOUND));
        airlineCompany.setIsActive(false);
        airlineCompanyRepository.save(airlineCompany);
    }
}
