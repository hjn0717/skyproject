package com.sky.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;

@Service
public class AddressBookServiceImpl implements AddressBookService {

	@Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public List<AddressBook> list() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        return addressBookMapper.list(addressBook);
    }

    @Override
    public void addAddressBook(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.insert(addressBook);
    }

    @Override
    public AddressBook getDefaultAddressBook() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        List<AddressBook> list = addressBookMapper.list(addressBook);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    @Override
    public void updateAddressBook(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    @Override
    public void deleteAddressBookById(Long id) {
        addressBookMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void setDefaultAddressBook(AddressBook addressBook) {
        AddressBook addressBook1 = new AddressBook();
        addressBook1.setUserId(BaseContext.getCurrentId());
        addressBook1.setIsDefault(0);
        addressBookMapper.updateIsDefaultByUserId(addressBook1);

        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }
}