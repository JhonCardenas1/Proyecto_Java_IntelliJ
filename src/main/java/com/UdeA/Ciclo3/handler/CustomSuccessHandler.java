package com.UdeA.Ciclo3.handler;

import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException {

        String targetURL = determineTargetUrl(authentication); //Llamamos al metodo que me da la url donde debo ir
        if (response.isCommitted()){
            System.out.println("No se puede redireccionar");
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetURL );
    }

    //Metodo que estrae el ROL de la persona que esta logueado
    protected String determineTargetUrl(Authentication authentication){
        String url ="";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<String>();

        for(GrantedAuthority a: authorities){
            roles.add(a.getAuthority());
        }

        if(esAdminitrativo(roles)){
            url ="/verEmpresas";

        } else if (esOperativo(roles)) {
            url ="/verMovimientos";

        }
        else {
            url="/Denegado";
        }
        return url;
    }

    private boolean esOperativo(List<String> roles){
        if (roles.contains("ROLE_USER")){
            return true;
        }
        return false;
    }

    private boolean esAdminitrativo(List<String> roles){
        if (roles.contains("ROLE_ADMIN")){
            return true;
        }
        return false;
    }


}
