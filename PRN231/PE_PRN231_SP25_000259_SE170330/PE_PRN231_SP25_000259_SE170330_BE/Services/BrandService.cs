using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Repositories;
using Repositories.Models;

namespace Services
{
    public interface IBrandService
    {
        Task<List<Brand>> GetAllAsync();
    }
    public class BrandService : IBrandService
    {
        private readonly BrandRepository _brandRepository;
        public BrandService() => _brandRepository = new BrandRepository();
        public async Task<List<Brand>> GetAllAsync() => await _brandRepository.GetAllAsync();
    }
}
