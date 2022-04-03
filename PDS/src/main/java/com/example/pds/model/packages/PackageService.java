package com.example.pds.model.packages;

import com.example.pds.config.CheckAuthentications;
import com.example.pds.model.user.User;
import com.example.pds.model.user.UserRepository;
import com.example.pds.util.exceptions.NotFoundException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageService {
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;

    public PackageSimpleResponseDTO sendPackage(int id, SendPackageDTO sendPackageDTO) {
        User recipient = userRepository.findByUsername(sendPackageDTO.getRecipient());

        Package currentPackage = new Package();
        currentPackage.setSender(userRepository.getById(id));

        currentPackage.setAddress(recipient.getAddress());
        currentPackage.setRecipient(recipient);


        Double volume = sendPackageDTO.getHeight() * sendPackageDTO.getWidth() * sendPackageDTO.getLength();
        currentPackage.setVolume(volume);
        currentPackage.setWeight(currentPackage.getWeight());
        currentPackage.setTrackingNumber(currentPackage.getTrackingNumber());
        currentPackage.setIsFragile(sendPackageDTO.getIsFragile());
        currentPackage.setIsSigned(sendPackageDTO.getIsSigned());
        currentPackage.setDescription(sendPackageDTO.getDescription());
        currentPackage.setWeight(sendPackageDTO.getWeight());

        packageRepository.save(currentPackage);

        return modelMapper.map(currentPackage, PackageSimpleResponseDTO.class);

    }

    public List<PackageComplexResponseDTO> getAllPackages() {
        List<PackageComplexResponseDTO> complexPackages = new ArrayList<>();
        List<Package> packages = packageRepository.findAll();
        for (Package package1 : packages) {
            complexPackages.add(modelMapper.map(package1, PackageComplexResponseDTO.class));
        }
        return complexPackages;
    }


    public PackageComplexResponseDTO getPackage(int id) {

        if (packageRepository.findById(id) == null) {
            throw new NotFoundException("Package does not exist");
        }
        Package package1 = packageRepository.getById(id);
        return modelMapper.map(package1, PackageComplexResponseDTO.class);
    }

    public List<PackageGetMyPackagesDTO> getAllPendingPackages() {
        List<PackageGetMyPackagesDTO> packageToReturn = new ArrayList<>();
        List<Package> packages = packageRepository.findAllByStatusId(1);
        for (Package pack : packages) {
            packageToReturn.add(modelMapper.map(pack, PackageGetMyPackagesDTO.class));

        }
        return packageToReturn;
    }

    public List<PackageGetMyPackagesDTO> getAllMyPackages(int id) {
        User user = userRepository.findById(id);
        List<Package> myPackages = packageRepository.findAllByRecipient(user);
        List <PackageGetMyPackagesDTO> dtoList = new ArrayList<>();
        for (Package pack : myPackages) {
            dtoList.add(modelMapper.map(pack, PackageGetMyPackagesDTO.class));
        }
        return dtoList;
    }
    public PackageGetMyPackagesDTO getMyPackage(int id, int userID){
        User user = userRepository.findById(id);
        if (id != userID){
            throw new UnauthorizedException("Not your package");
        }
        Package myPackage = packageRepository.findByRecipient(user);
        return modelMapper.map(myPackage,PackageGetMyPackagesDTO.class);
    }

}

