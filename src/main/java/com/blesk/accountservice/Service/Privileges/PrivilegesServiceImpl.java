package com.blesk.accountservice.Service.Privileges;

import com.blesk.accountservice.DAO.Privileges.PrivilegesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrivilegesServiceImpl implements PrivilegesService {

    private PrivilegesDAOImpl privilegeDAO;

    @Autowired
    public PrivilegesServiceImpl(PrivilegesDAOImpl privilegeDAO) {
        this.privilegeDAO = privilegeDAO;
    }

    @Override
    @Transactional
    public Privileges createPrivilege(Privileges privileges) {
        if (this.privilegeDAO.save(privileges) == null)
            throw new AccountServiceException(Messages.CREATE_PRIVILEGE);
        return privileges;
    }

    @Override
    @Transactional
    public Boolean deletePrivilege(Long privilegeId) {
        Privileges privileges = this.privilegeDAO.get(Privileges.class, privilegeId);
        if (privileges == null)
            throw new AccountServiceException(Messages.DELETE_GET_PRIVILEGE);
        if (!this.privilegeDAO.delete(privileges))
            throw new AccountServiceException(Messages.DELETE_PRIVILEGE);
        return true;
    }

    @Override
    @Transactional
    public Boolean updatePrivilege(Privileges privileges) {
        if (!this.privilegeDAO.update(privileges))
            throw new AccountServiceException(Messages.UPDATE_PRIVILEGE);
        return true;
    }

    @Override
    @Transactional
    public Privileges getPrivilege(Long privilegeId) {
        Privileges privilege = this.privilegeDAO.get(Privileges.class, privilegeId);
        if (privilege == null)
            throw new AccountServiceException(Messages.GET_PRIVILEGE);
        return privilege;
    }

    @Override
    @Transactional
    public List<Privileges> getAllPrivileges(int pageNumber, int pageSize) {
        List<Privileges> privileges = this.privilegeDAO.getAll(Privileges.class, pageNumber, pageSize);
        if (privileges == null)
            throw new AccountServiceException(Messages.GET_ALL_PRIVILEGES);
        return privileges;
    }

    @Override
    @Transactional
    public Privileges getPrivilegeByName(String privilegeName) {
        Privileges privilege = this.privilegeDAO.getPrivilegeByName(privilegeName);
        if (privilege == null)
            throw new AccountServiceException(Messages.GET_PRIVILEGE_BY_NAME);
        return privilege;
    }

    @Override
    @Transactional
    public Boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        if (value == null || fieldName == null)
            throw new UnsupportedOperationException(String.format(Messages.UNSUPPORTED_COLUMN, fieldName));

        return this.privilegeDAO.unique(Accounts.class, fieldName, value.toString());
    }
}