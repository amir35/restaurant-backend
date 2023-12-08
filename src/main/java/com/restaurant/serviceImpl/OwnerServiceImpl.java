package com.restaurant.serviceImpl;

import com.amir35.logutil.TimingLog;
import com.restaurant.entity.Owner;
import com.restaurant.entity.OwnerDto;
import com.restaurant.repository.OwnerRepository;
import com.restaurant.responseMessage.OwnerResponse;
import com.restaurant.service.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OwnerResponse<OwnerDto> authenticateUser(String username) {
        Optional<Owner> owner = Optional.ofNullable(ownerRepository.findByUsername(username));

        OwnerDto ownerDto;
        if(owner.isPresent())
        {
            ownerDto = modelMapper.map(owner.get(), OwnerDto.class);
            return new OwnerResponse<>(ownerDto, "Owner Found");
        } else {
            return new OwnerResponse<>(null, "Owner Not Found");
        }
    }

    @TimingLog
    public OwnerResponse<OwnerDto> findByOwnerId(int ownerId) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);

        OwnerDto ownerDto;
        if(owner.isPresent())
        {
            ownerDto = modelMapper.map(owner.get(), OwnerDto.class);
            return new OwnerResponse<>(ownerDto, "Owner Found");
        } else {
            return new OwnerResponse<>(null, "Owner Not Found");
        }

    }

    @TimingLog
    @Override
    public OwnerResponse<List<OwnerDto>> getAllOwners() {

        List<Owner> owners = ownerRepository.findAll();

        log.info("All Owners: " , owners);

        if(owners.isEmpty())
        {
            return new OwnerResponse<>(null, "Owners List is Empty");
        } else {
            List<OwnerDto> ownerDtoList = owners.stream().map(
                    owner -> modelMapper.map(owner, OwnerDto.class)
            ).collect(Collectors.toList());

            return new OwnerResponse<>(ownerDtoList, "Owners List is fetched");
        }

    }

    @TimingLog
    @Override
    public OwnerResponse<OwnerDto> saveOwner(OwnerDto ownerDto) {

        OwnerResponse ownerResponse;

        Owner student = modelMapper.map(ownerDto, Owner.class);

        Owner saveStudent = ownerRepository.save(student);
            ownerResponse = new OwnerResponse(modelMapper.map(saveStudent, OwnerDto.class), "Owner inserted");

        return ownerResponse;

    }

    @TimingLog
    @Override
    public OwnerResponse<OwnerDto> updateOwner(int id, OwnerDto owner) {

        owner.setOwnerId(id);

        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        OwnerResponse ownerResponse;

        if(ownerOptional.isPresent())
        {
            Owner updateOwner = ownerRepository.save(modelMapper.map(owner, Owner.class));
            ownerResponse = new OwnerResponse(modelMapper.map(updateOwner, OwnerDto.class), "Owner Record successfully updated");
        }else
        {
            ownerResponse = new OwnerResponse(null, "Owner Record not updated");
        }

        return ownerResponse;
    }

    @TimingLog
    @Override
    public OwnerResponse<OwnerDto> deleteOwner(int id) {

        Optional<Owner> ownerOptional = ownerRepository.findById(id);

        OwnerResponse ownerResponse;

        if(ownerOptional.isPresent())
        {
            ownerRepository.deleteById(id);
            ownerResponse = new OwnerResponse(modelMapper.map(ownerOptional.get(), OwnerDto.class), "Owner successfully deleted");
        } else {
            ownerResponse = new OwnerResponse(null, "Owner Not deleted");
        }

        return ownerResponse;
    }
}