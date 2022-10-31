package by.krutikov.mappers;

import by.krutikov.domain.Account;
import by.krutikov.dto.request.AccountDetails;
import by.krutikov.dto.request.AccountDetailsUpdate;
import by.krutikov.dto.response.AccountDetailsResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = RoleMapper.class)
public interface AccountMapper {
    Account map(AccountDetails request);

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
