package com.blesk.accountservice.Service.Privileges;

import com.blesk.accountservice.DAO.Privileges.PrivilegesDAOImpl;
import com.blesk.accountservice.DTO.JwtMapper;
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
    public Privileges createPrivilege(Privileges privileges, JwtMapper jwtMapper) {
        Privileges privilege = this.privilegeDAO.save(privileges);
        if (privilege == null)
            throw new AccountServiceException(Messages.CREATE_PRIVILEGE);
        return privilege;
    }

    @Override
    @Transactional
    public Boolean softDeletePrivilege(Long privilegeId, JwtMapper jwtMapper) {
        Privileges privileges = this.privilegeDAO.get(privilegeId, false);
        if (privileges == null)
            throw new AccountServiceException(Messages.DELETE_GET_PRIVILEGE);
        if (!this.privilegeDAO.softDelete(privileges))
            throw new AccountServiceException(Messages.DELETE_PRIVILEGE);
        return true;
    }

    @Override
    @Transactional
    public Boolean deletePrivilege(Long privilegeId) {
        Privileges privileges = this.privilegeDAO.get(Privileges.class, privilegeId);
        if (privileges == null)
            throw new AccountServiceException(Messages.DELETE_GET_PRIVILEGE);
        if (!this.privilegeDAO.delete("privileges", "privilege_id", privilegeId))
            throw new AccountServiceException(Messages.DELETE_PRIVILEGE);
        return true;
    }

    @Override
    @Transactional
    public Boolean updatePrivilege(Privileges privileges, JwtMapper jwtMapper) {
        if (!this.privilegeDAO.update(privileges))
            throw new AccountServiceException(Messages.UPDATE_PRIVILEGE);
        return true;
    }

    @Override
    @Transactional
    public Privileges getPrivilege(Long privilegeId, boolean isDeleted) {
        Privileges privilege = this.privilegeDAO.get(privilegeId, isDeleted);
        if (privilege == null)
            throw new AccountServiceException(Messages.GET_PRIVILEGE);
        return privilege;
    }

    @Override
    @Transactional
    public Privileges findPrivilegeByName(String name, boolean isDeleted) {
        Privileges privilege = this.privilegeDAO.getItemByColumn("name", name, isDeleted);
        if (privilege == null)
            throw new AccountServiceException(Messages.GET_PRIVILEGE);
        return privilege;
    }

    @Override
    @Transactional
    public List<Privileges> getAllPrivileges(int pageNumber, int pageSize, boolean isDeleted) {
        List<Privileges> privileges = this.privilegeDAO.getAll(pageNumber, pageSize, isDeleted);
        if (privileges == null)
            throw new AccountServiceException(Messages.GET_ALL_PRIVILEGES);
        return privileges;
    }

    @Override
    @Transactional
    public Map<String, Object> searchForPrivileges(HashMap<String, HashMap<String, String>> criteria, boolean isDeleted) {
        if (criteria.get(Keys.PAGINATION) == null)
            throw new AccountServiceException(Messages.PAGINATION_ERROR);

        Map<String, Object> privileges = this.privilegeDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), isDeleted);

        if (privileges == null || privileges.isEmpty())
            throw new AccountServiceException(Messages.SEARCH_ERROR);

        return privileges;
    }
}