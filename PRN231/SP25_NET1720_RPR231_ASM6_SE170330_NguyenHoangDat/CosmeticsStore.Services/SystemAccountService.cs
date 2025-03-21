using CosmeticsStore.Repositories;
using CosmeticsStore.Repositories.Models;

namespace CosmeticsStore.Services
{
    public interface ISystemAccountService
    {
        Task<SystemAccount> Authenticate(string email, string password);
    }
    public class SystemAccountService : ISystemAccountService
    {
        private readonly SystemAccountRepository _systemAccountRepository;
        public SystemAccountService() => _systemAccountRepository = new SystemAccountRepository();
        public async Task<SystemAccount> Authenticate(string email, string password) => await _systemAccountRepository.GetSystemAccount(email, password);

    }
}
