// See https://aka.ms/new-console-template for more information
using Grpc.Net.Client;
using SCBS.GrpcClient.Protos;

Console.WriteLine("Hello, Welcome to gRPC Client!");

using var channel = GrpcChannel.ForAddress("https://localhost:7246");
var client = new ScheduleProto.ScheduleProtoClient(channel);

var schedules = client.GetAll(new EmptyRequest());
Console.WriteLine("Get All.\n");
if (schedules != null && schedules.Items.Count > 0)
{
    foreach (var item in schedules.Items)
    {
        Console.WriteLine(string.Format("Id:{0} - UserId:{1} - WorkDate:{2} - Status:{3} - CreatedAt:{4} - UpdatedAt:{5}\n", item.Id, item.UserId, item.WorkDate, item.Status, item.CreatedAt, item.UpdatedAt));
    }
}
else
{
    Console.WriteLine("No schedules found.\n");
}

Console.WriteLine("Find by Id.\n");
var getById = Console.ReadLine();
if (!string.IsNullOrEmpty(getById))
{
    var schedule = client.GetById(new ScheduleIdRequest { Id = getById });
    if (schedule != null)
    {
        Console.WriteLine(string.Format("Id:{0} - UserId:{1} - WorkDate:{2} - Status:{3} - CreatedAt:{4} - UpdatedAt:{5}\n", schedule.Id, schedule.UserId, schedule.WorkDate, schedule.Status, schedule.CreatedAt, schedule.UpdatedAt));
    }
    else
    {
        Console.WriteLine("No schedule found.\n");
    }
}

Console.WriteLine("Create.\n");
var newId = Console.ReadLine();
var newUserId = Console.ReadLine();
var newWorkDate = Console.ReadLine();
var newStatus = Console.ReadLine();
if (newId != null && newUserId != null && newWorkDate != null && newStatus != null)
{
    var newSchedule = client.Create(new ScheduleItem { Id = newId, UserId = newUserId, WorkDate = newWorkDate, Status = newStatus, CreatedAt = DateTime.Now.ToString(), UpdatedAt = DateTime.Now.ToString() });
    Console.WriteLine(string.Format("Status:{0} - Message:{1} - Data:\n", newSchedule.Status, newSchedule.Message));
    foreach (var item in newSchedule.Data.Items)
    {
        Console.WriteLine(string.Format("Id:{0} - UserId:{1} - WorkDate:{2} - Status:{3} - CreatedAt:{4} - UpdatedAt:{5}\n", item.Id, item.UserId, item.WorkDate, item.Status, item.CreatedAt, item.UpdatedAt));
    }
}

Console.WriteLine("Update.\n");
var updateId = Console.ReadLine();
var updateUserId = Console.ReadLine();
var updateWorkDate = Console.ReadLine();
var updateStatus = Console.ReadLine();
if (updateId != null && updateUserId != null && updateWorkDate != null && updateStatus != null)
{
    var updateSchedule = client.Update(new ScheduleItem { Id = updateId, UserId = updateUserId, WorkDate = updateWorkDate, Status = updateStatus, CreatedAt = DateTime.Now.ToString(), UpdatedAt = DateTime.Now.ToString() });
    Console.WriteLine(string.Format("Status:{0} - Message:{1} - Data:\n", updateSchedule.Status, updateSchedule.Message));
    foreach (var item in updateSchedule.Data.Items)
    {
        Console.WriteLine(string.Format("Id:{0} - UserId:{1} - WorkDate:{2} - Status:{3} - CreatedAt:{4} - UpdatedAt:{5}\n", item.Id, item.UserId, item.WorkDate, item.Status, item.CreatedAt, item.UpdatedAt));
    }
}

Console.WriteLine("Delete.\n");
var deleteId = Console.ReadLine();
if (deleteId != null)
{
    var deleteSchedule = client.Delete(new ScheduleIdRequest { Id = deleteId });
    Console.WriteLine(string.Format("Status:{0} - Message:{1} - Data:\n", deleteSchedule.Status, deleteSchedule.Message));
    foreach (var item in deleteSchedule.Data.Items)
    {
        Console.WriteLine(string.Format("Id:{0} - UserId:{1} - WorkDate:{2} - Status:{3} - CreatedAt:{4} - UpdatedAt:{5}\n", item.Id, item.UserId, item.WorkDate, item.Status, item.CreatedAt, item.UpdatedAt));
    }
}