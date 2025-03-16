using Grpc.Core;
using SCBS.Services;
using SCBS.GrpcServices.Protos;

namespace SCBS.GrpcServices.Services
{
    public class UserGrpcService : UserGrpc.UserGrpcBase
    {
        private readonly IUserService _userService;
        public UserGrpcService(IUserService userService)
        {
            _userService = userService;
        }
        public override async Task<UserList> GetAllUsers(EmptyUserRequest request, ServerCallContext context)
        {
            var users = await _userService.GetAllAsync();
            var itemList = new UserList();
            itemList.Items.AddRange(users.Select(user => new UserItem
            {
                Id = user.Id.ToString(),
                Username = user.Username
            }));
            return itemList;
        }
    }
}
