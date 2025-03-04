using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using GraphQL.Types;
using Microsoft.EntityFrameworkCore;
using SCBS.Repositories.Models;

namespace SCBS.GraphQLAPIServices.GraphQLs
{
    public class ScheduleType : ObjectGraphType<Schedule>
    {
        public ScheduleType()
        {
            Field(s => s.Id);
            Field(s => s.UserId);
            Field(s => s.WorkDate);
            Field(s => s.Status);
            Field(s => s.CreatedAt);
            Field(s => s.UpdatedAt);
            Field(s => s.Title);
            Field(s => s.Description);
            Field(s => s.Location);
            Field(s => s.Notes);
        }
    }
}
