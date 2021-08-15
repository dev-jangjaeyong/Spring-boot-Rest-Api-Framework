package com.atonm.kblease.api.config.security.custom;

import com.atonm.kblease.api.common.entity.ResUserInfo;
import com.atonm.kblease.api.common.entity.TaMember;
import com.atonm.kblease.api.dto.KbOrgInationDTO;
import com.atonm.kblease.api.repository.UserJpaRepository;
import com.atonm.kblease.api.utils.ModelMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Optional;


/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private UserJpaRepository userJpaRepository;

    @Autowired
    public CustomUserDetailsService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<TaMember> user = userJpaRepository.findByUserId(userId);

        if(!user.isPresent()) {
            logger.info("존재하지 않는 아이디입니다 : " + userId);
            throw new UsernameNotFoundException(userId);
        }

        return new ResUserInfo(user.get());
        /*SessionUser sessionUser = new SessionUser();

        if (user != null) {
            sessionUser = ModelMapperUtils.map(user, SessionUser.class);
            sessionUser.setKbOrgInationDTO(ModelMapperUtils.map(user.get().getTaOrgination(), KbOrgInationDTO.class));
        } else {
            throw new UsernameNotFoundException("USERNAME_NOT_FOUND");
        }

        return sessionUser;*/
    }
}
