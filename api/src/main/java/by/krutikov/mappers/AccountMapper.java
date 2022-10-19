package by.krutikov.mappers;

import by.krutikov.domain.Account;
import by.krutikov.dto.request.AccountInfo;
import by.krutikov.dto.response.CreateAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account map(AccountInfo request);

    CreateAccountResponse map(Account account);

    void update(@MappingTarget Account account, AccountInfo request);
}
