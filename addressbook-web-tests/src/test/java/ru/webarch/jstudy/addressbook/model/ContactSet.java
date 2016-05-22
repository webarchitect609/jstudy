package ru.webarch.jstudy.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactSet extends ForwardingSet<ContactData> {

    private Set<ContactData> delegate;

    public ContactSet(ContactSet contactSet) {
        this.delegate = new HashSet<>(contactSet.delegate());
    }

    public ContactSet() {
        this.delegate = new HashSet<>();
    }

    public ContactSet(Collection<ContactData> contactDataCollection) {
        this.delegate = new HashSet<>(contactDataCollection);
    }

    @Override
    protected Set<ContactData> delegate() {
        return delegate;
    }

    public ContactSet with(ContactData contact) {
        ContactSet contactSet = new ContactSet(this);
        contactSet.add(contact);
        return contactSet;
    }

    public ContactSet without(ContactData contact) {
        ContactSet contactSet = new ContactSet(this);
        contactSet.remove(contact);
        return contactSet;
    }
}
