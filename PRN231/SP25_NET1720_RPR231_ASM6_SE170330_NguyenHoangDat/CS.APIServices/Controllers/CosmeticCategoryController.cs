using CS.Repositories.Models;
using CS.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.OData.Query;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace CS.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    [EnableQuery]
    public class CosmeticCategoryController : ControllerBase
    {
        private readonly ICosmeticCategoryService _cosmeticCategoryService;
        public CosmeticCategoryController(ICosmeticCategoryService cosmeticCategoryService) => _cosmeticCategoryService = cosmeticCategoryService;
        // GET: api/<CosmeticCategoryController>
        [HttpGet]
        [Authorize(Roles = "1")]
        public async Task<IEnumerable<CosmeticCategory>> Get() => await _cosmeticCategoryService.GetAllAsync();

        // GET api/<CosmeticCategoryController>/5
        [HttpGet("{id}")]
        [Authorize(Roles = "1")]
        public async Task<CosmeticCategory> Get(string id) => await _cosmeticCategoryService.GetByIdAsync(id);

        // POST api/<CosmeticCategoryController>
        [HttpPost]
        [Authorize(Roles = "1")]
        public async Task<int> Post([FromBody] CosmeticCategory cosmeticCategory) => await _cosmeticCategoryService.CreateAsync(cosmeticCategory);

        // PUT api/<CosmeticCategoryController>/5
        [HttpPut]
        [Authorize(Roles = "1")]
        public async Task<int> Put(int id, [FromBody] CosmeticCategory cosmeticCategory) => await _cosmeticCategoryService.Update(cosmeticCategory);

        // DELETE api/<CosmeticCategoryController>/5
        [HttpDelete("{id}")]
        [Authorize(Roles = "1")]
        public async Task<bool> Delete(string id) => await _cosmeticCategoryService.RemoveAsync(id);
    }
}
