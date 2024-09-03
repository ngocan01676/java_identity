package com.devteria.identity_service.mapper;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.User;
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

        User user = new User();

        user.setUsername( userCreationRequest.getUsername() );
        user.setPassword( userCreationRequest.getPassword() );
        user.setFirstName( userCreationRequest.getFirstName() );
        user.setLastName( userCreationRequest.getLastName() );
        user.setDob( userCreationRequest.getDob() );

        return user;
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
        userResponse.password( user.getPassword() );
        userResponse.firstName( user.getFirstName() );
        userResponse.lastName( user.getLastName() );
        userResponse.dob( user.getDob() );

        return userResponse.build();
    }
}
