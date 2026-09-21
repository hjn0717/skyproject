package com.sky.service;

import java.util.List;

import com.sky.entity.AddressBook;

public interface AddressBookService {

	List<AddressBook> list();
    void addAddressBook(AddressBook addressBook);
    AddressBook getDefaultAddressBook();
    AddressBook getById(Long id);
    void updateAddressBook(AddressBook addressBook);
    void deleteAddressBookById(Long id);
    void setDefaultAddressBook(AddressBook addressBook);
}