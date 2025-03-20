using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using WP.Repositories.Models;
using WP.Services;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace WP.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class StyleController : ControllerBase
    {
        private readonly IStyleService _styleService;
        public StyleController(IStyleService styleService) => _styleService = styleService;
        // GET: api/<StyleController>
        [HttpGet]
        [Authorize(Roles = "3,2")]
        public async Task<IEnumerable<Style>> Get() => await _styleService.GetAllAsync();
    }
}
