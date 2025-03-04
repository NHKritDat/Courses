using GraphQL.Types;
using SCBS.Repositories;

namespace SCBS.GraphQLAPIServices.GraphQLs
{
    public class ScheduleQuery : ObjectGraphType
    {
        public ScheduleQuery(ScheduleRepository scheduleRepository)
        {
            Field<ListGraphType<ScheduleType>>(
                "schedules",
                resolve: context => scheduleRepository.GetAllAsync()
            );
        }
    }
}
