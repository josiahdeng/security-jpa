package com.lucian.security_jpa.config;

import com.lucian.security_jpa.entity.Menu;
import com.lucian.security_jpa.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource {
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    MenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        if (isAllowedRequest((FilterInvocation) o)) return null;
        List<Menu> allMenus = menuService.getAllMenus();
        List<ConfigAttribute> collect = allMenus.stream().
                filter(menu -> antPathMatcher.match(menu.getPattern(), requestUrl))
                .map(Menu::getRoles).flatMap(Collection::stream)
                .map(role -> new SecurityConfig(role.getName()))
                .collect(Collectors.toList());
        return collect.size() > 0 ? collect : SecurityConfig.createList("ROLE_login");
//        for (Menu menu : allMenus) {
//            if (antPathMatcher.match(menu.getPattern(), requestUrl)){
//                List<Role> roles = menu.getRoles();
//                return roles.stream().map(role -> new SecurityConfig(role.getName())).collect(Collectors.toList());
////                String[] rolesStr = new String[roles.size()];
////                for (int i = 0; i < roles.size(); i++) {
////                    rolesStr[i] = roles.get(i).getName();
////                }
////                return SecurityConfig.createList(rolesStr);
//            }
//        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    private Boolean isAllowedRequest(FilterInvocation fi){
        return allowedRequest().stream().map(AntPathRequestMatcher::new)
                .filter(antPathRequestMatcher -> antPathRequestMatcher.matches(fi.getHttpRequest()))
                .toArray().length > 0;
    }

    private List<String> allowedRequest(){
        return Arrays.asList("/hello", "/js/**", "/css/**");
    }
}
