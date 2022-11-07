package by.krutikov.mappers;

import by.krutikov.domain.Account;
import by.krutikov.dto.request.AccountDetails;
import by.krutikov.dto.request.AccountDetailsUpdate;
import by.krutikov.dto.response.AccountDetailsResponse;
import by.krutikov.security.encoder.EncodedMapping;
import by.krutikov.security.encoder.PasswordEncoderMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {RoleMapper.class, PasswordEncoderMapper.class})
public interface AccountMapper {
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    Account map(AccountDetails request);

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    Account map(AccountDetailsUpdate request);

    @Mapping(source = "userProfile.id", target = "profileId")
    AccountDetailsResponse map(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Account account, AccountDetailsUpdate request);

    default List<AccountDetailsResponse> toResponseList(List<Account> accounts) {
        return accounts.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}