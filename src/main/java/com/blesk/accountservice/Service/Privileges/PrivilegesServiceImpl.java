package com.blesk.accountservice.Service.Privileges;

import com.blesk.accountservice.DAO.Privileges.PrivilegesDAOImpl;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Value.Keys;
import com.blesk.accountservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return this.privilegeDAO.save(privileges);
    }

    @Override
    @Transactional
    public Boolean deletePrivilege(Long privilegeId) {
        Privileges privileges = this.privilegeDAO.get(Privileges.class, privilegeId);
        if (privileges == null)
            throw new AccountServiceException(Messages.GET_PRIVILEGE);
        if (!this.privilegeDAO.delete("privileges", "privilege_id", privilegeId))
            throw new AccountServiceException(Messages.DELETE_PRIVILEGE);
        return true;
    }

    @Override
    @Transactional
    public Boolean updatePrivilege(Privileges privileges) {
        return this.privilegeDAO.update(privileges);
    }

    @Override
    @Transactional
    public Privileges getPrivilege(Long privilegeId) {
        return this.privilegeDAO.get(Privileges.class, privilegeId);
    }

    @Override
    @Transactional
    public Privileges findPrivilegeByName(String name) {
        Privileges privilege = this.privilegeDAO.getItemByColumn(Privileges.class, "name", name);
        if (privilege == null)
            throw new AccountServiceException(Messages.GET_PRIVILEGE);
        return privilege;
    }

    @Override
    @Transactional
    public List<Privileges> getAllPrivileges(int pageNumber, int pageSize) {
        return this.privilegeDAO.getAll(Privileges.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public Map<String, Object> searchForPrivilege(HashMap<String, HashMap<String, String>> criteria) {
        return this.privilegeDAO.searchBy(Privileges.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
    }
}