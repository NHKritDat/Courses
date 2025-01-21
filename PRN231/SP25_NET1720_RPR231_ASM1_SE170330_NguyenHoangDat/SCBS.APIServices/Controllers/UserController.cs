using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using SCBS.Repositories.Models;
using SCBS.Services;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace SCBS.APIServices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;
        public UserController(IUserService userService) => _userService = userService;
        // GET: api/<UserController>
        [HttpGet]
        [Authorize(Roles = "1,2")]
        public async Task<IEnumerable<User>> Get()
        {
            return await _userService.GetAll();
        }

        // GET api/<UserController>/5
        [HttpGet("{id}")]
        [Authorize(Roles = "1,2")]
        public async Task<User?> Get(Guid id)
        {
            return await _userService.GetById(id);
        }

        [HttpGet("{username?}/{email?}/{fullName?}")]
        [Authorize(Roles = "1,2")]
        public async Task<IEnumerable<User?>> Get(string username, string email, string fullName)
        {
            return await _userService.Search(username, email, fullName);
        }

        // POST api/<UserController>
        [HttpPost]
        [Authorize(Roles = "1,2")]
        public async Task<int> Post(User user)
        {
            return await _userService.Create(user);
        }

        // PUT api/<UserController>/5
        [HttpPut]
        [Authorize(Roles = "1,2")]
        public async Task<int> Put(User user)
        {
            return await _userService.Update(user);
        }

        // DELETE api/<UserController>/5
        [HttpDelete("{id}")]
        [Authorize(Roles = "1")]
        public async Task<bool> Delete(Guid id)
        {
            return await _userService.Delete(id);
        }
    }
}
