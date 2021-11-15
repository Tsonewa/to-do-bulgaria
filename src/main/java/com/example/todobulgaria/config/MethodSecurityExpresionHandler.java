package com.example.todobulgaria.config;

import com.example.todobulgaria.services.TripEntityService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class MethodSecurityExpresionHandler extends
        DefaultMethodSecurityExpressionHandler {


        private final TripEntityService tripEntityService;

    public MethodSecurityExpresionHandler(TripEntityService tripEntityService) {
        this.tripEntityService = tripEntityService;
    }


    @Override
        protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, MethodInvocation invocation) {
        OwnerSecurityExpressionRoot root = new OwnerSecurityExpressionRoot(authentication);

            root.setTripService(tripEntityService);
            root.setPermissionEvaluator(getPermissionEvaluator());
            root.setTrustResolver(new AuthenticationTrustResolverImpl());
            root.setRoleHierarchy(getRoleHierarchy());

            return root;

        }
}
