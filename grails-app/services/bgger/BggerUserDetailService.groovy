package bgger

import bgger.security.User
import grails.transaction.Transactional
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * Created by mikeh on 9/11/15.
 */
class BggerUserDetailService implements UserDetailsService{

        @Transactional(readOnly=true, noRollbackFor=[IllegalArgumentException, UsernameNotFoundException])
        UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            User user = User.findByUsername(username)
            if (!user) throw new UsernameNotFoundException('User not found', username)

            def authorities = user.authorities.collect {
                new GrantedAuthorityImpl(it.authority)
            }

            return new BggUserDetails(user.username, user.password, user.enabled,
                    !user.accountExpired, !user.passwordExpired,
                    !user.accountLocked, authorities ?: NO_ROLES, user.id,
                    )
        }
}

