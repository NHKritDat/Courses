using CosmeticsStore.Repositories.Models;
using CosmeticsStore.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.OData.Query;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace CosmeticsStore.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    [EnableQuery]
    public class CosmeticInformationController : ControllerBase
    {
        private readonly ICosmeticInformationService _cosmeticInformationService;
        public CosmeticInformationController(ICosmeticInformationService cosmeticInformationService) => _cosmeticInformationService = cosmeticInformationService;
        // GET: api/<CosmeticInformationController>
        [HttpGet]
        [Authorize(Roles = "1,3,4")]
        public async Task<IEnumerable<CosmeticInformation>> Get() => await _cosmeticInformationService.GetAllAsync();

        // GET api/<CosmeticInformationController>/5
        [HttpGet("{id}")]
        [Authorize(Roles = "1")]
        public async Task<CosmeticInformation> Get(string id) => await _cosmeticInformationService.GetByIdAsync(id);

        // POST api/<CosmeticInformationController>
        [HttpPost]
        [Authorize(Roles = "1")]
        public async Task<int> Post([FromBody] CosmeticInformation value) => await _cosmeticInformationService.CreateAsync(value);

        // PUT api/<CosmeticInformationController>/5
        [HttpPut("{id}")]
        [Authorize(Roles = "1")]
        public async Task<int> Put(string id, [FromBody] CosmeticInformation value) => await _cosmeticInformationService.UpdateAsync(value);

        // DELETE api/<CosmeticInformationController>/5
        [HttpDelete("{id}")]
        [Authorize(Roles = "1")]
        public async Task<bool> Delete(string id) => await _cosmeticInformationService.RemoveAsync(id);
    }
}
