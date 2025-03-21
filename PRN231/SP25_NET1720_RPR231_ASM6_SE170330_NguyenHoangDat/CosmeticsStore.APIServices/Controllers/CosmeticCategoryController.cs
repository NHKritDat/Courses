using CosmeticsStore.Repositories.Models;
using CosmeticsStore.Services;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace CosmeticsStore.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CosmeticCategoryController : ControllerBase
    {
        private readonly ICosmeticCategoryService _cosmeticCategoryService;
        public CosmeticCategoryController(ICosmeticCategoryService cosmeticCategoryService) => _cosmeticCategoryService = cosmeticCategoryService;
        // GET: api/<CosmeticCategoryController>
        [HttpGet]
        public async Task<IEnumerable<CosmeticCategory>> Get() => await _cosmeticCategoryService.GetAllAsync();
    }
}
