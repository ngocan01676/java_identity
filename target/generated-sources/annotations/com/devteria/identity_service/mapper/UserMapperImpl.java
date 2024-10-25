package com.devteria.identity_service.mapper;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest userCreationRequest) {
        if ( userCreationRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( userCreationRequest.getUsername() );
        user.password( userCreationRequest.getPassword() );
        user.firstName( userCreationRequest.getFirstName() );
        user.lastName( userCreationRequest.getLastName() );
        user.dob( userCreationRequest.getDob() );

        return user.build();
    }

    @Override
    public void updateUser(User user, UserUpdateRequest userUpdateRequest) {
        if ( userUpdateRequest == null ) {
            return;
        }

        user.setPassword( userUpdateRequest.getPassword() );
        user.setFirstName( userUpdateRequest.getFirstName() );
        user.setLastName( userUpdateRequest.getLastName() );
        user.setDob( userUpdateRequest.getDob() );
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.username( user.getUsername() );
        userResponse.id( user.getId() );
        userResponse.firstName( user.getFirstName() );
        userResponse.lastName( user.getLastName() );
        userResponse.dob( user.getDob() );
        Set<String> set = user.getRoles();
        if ( set != null ) {
            userResponse.roles( new LinkedHashSet<String>( set ) );
        }

        return userResponse.build();
    }
}
