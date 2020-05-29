package com.blesk.accountservice.Service.Privileges;

import com.blesk.accountservice.DAO.Privileges.PrivilegesDAOImpl;
import com.blesk.accountservice.Model.Privileges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
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
    @Lock(value = LockModeType.WRITE)
    public Privileges createPrivilege(Privileges privileges) {
        return this.privilegeDAO.save(privileges);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deletePrivilege(Long privilegeId) {
        return this.privilegeDAO.delete("privileges", "privilege_id", privilegeId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updatePrivilege(Privileges privilege, Privileges privileges) {
        privilege.setName(privileges.getName());
        return this.privilegeDAO.update(privilege);
    }

    @Override
    @Transactional
    public Privileges getPrivilege(Long privilegeId) {
        return this.privilegeDAO.get(Privileges.class, privilegeId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Privileges findPrivilegeByName(String name) {
        return this.privilegeDAO.getItemByColumn(Privileges.class, "name", name);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Privileges> getAllPrivileges(int pageNumber, int pageSize) {
        return this.privilegeDAO.getAll(Privileges.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForPrivilege(HashMap<String, HashMap<String, String>> criterias) {
        return this.privilegeDAO.searchBy(Privileges.class, criterias);
    }
}