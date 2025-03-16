using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using SCBS.Repositories.Models;
using SCBS.Services;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace SCBS.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;
        public UserController(IUserService userService) => _userService = userService;
        // GET: api/<UserController>
        [HttpGet]
        public async Task<IEnumerable<User>> Get() => await _userService.GetAllAsync();

        // GET api/<UserController>/5
        [HttpGet("{id}")]
        public async Task<User> Get(Guid id) => await _userService.GetByIdAsync(id);
        [HttpPost]
        public async Task<int> Post([FromBody] User user) => await _userService.CreateAsync(user);
        [HttpPut]
        public async Task<int> Put([FromBody] User user) => await _userService.UpdateAsync(user);
        [HttpDelete("{id}")]
        public async Task<bool> Delete(Guid id) => await _userService.DeleteAsync(id);
    }
}
