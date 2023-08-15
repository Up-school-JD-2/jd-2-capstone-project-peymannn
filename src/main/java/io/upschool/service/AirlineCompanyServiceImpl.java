package io.upschool.service;

import io.upschool.Validation.EmailValidationImpl;
import io.upschool.dto.request.AirlineCompanySaveRequest;
import io.upschool.dto.response.AirlineCompanySaveResponse;
import io.upschool.entity.AirlineCompany;
import io.upschool.exception.BusinessException;
import io.upschool.exception.EmailNotValidException;
import io.upschool.repository.AirlineCompanyRepository;
import io.upschool.service.Interface.AirlineCompanyService;
import io.upschool.utils.AirlineSystemConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        AirlineCompany airport = getAirlineCompany(request);
        AirlineCompany savedAirport = airlineCompanyRepository.save(airport);
        return getAirlineCompanySaveResponse(savedAirport);
    }

    @Override
    public List<AirlineCompanySaveResponse> searchAirlineCompaniesByName(String name) {
        var filteredAirlineCompany = airlineCompanyRepository.findAirlineCompaniesByNameContainingIgnoreCase(name);
        List<AirlineCompanySaveResponse> list = filteredAirlineCompany.stream().map(airlineCompany -> getAirlineCompanySaveResponse(airlineCompany)).toList();
        return list;
    }

    @Override
    public AirlineCompany getReferenceById(Long id) {
        return airlineCompanyRepository.getReferenceById(id);
    }

    @Override
    public void deleteById(Long id) {
        var airlineCompany = airlineCompanyRepository.findById(id).orElseThrow(() -> new BusinessException(AirlineSystemConstant.DATA_NOT_FOUND));
        airlineCompany.setIsActive(false);
        airlineCompanyRepository.save(airlineCompany);
    }

    private static AirlineCompany getAirlineCompany(AirlineCompanySaveRequest request) {
        return AirlineCompany
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }

    private static AirlineCompanySaveResponse getAirlineCompanySaveResponse(AirlineCompany airlineCompany) {
        return AirlineCompanySaveResponse.builder()
                .name(airlineCompany.getName())
                .email(airlineCompany.getEmail())
                .build();
    }

    private static void CheckValidations(AirlineCompanySaveRequest request) {
        if (!EmailValidationImpl.isValid(request.getEmail()))
            throw new EmailNotValidException(AirlineSystemConstant.INVALID_EMAIL_EXCEPTION);
    }


}
