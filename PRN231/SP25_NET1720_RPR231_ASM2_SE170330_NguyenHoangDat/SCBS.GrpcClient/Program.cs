using Grpc.Net.Client;
using SCBS.GrpcClient.Protos;

Console.WriteLine("Hello, Welcome to gRPC Client!");
// The port number must match the port of the gRPC server.
using var channel = GrpcChannel.ForAddress("https://localhost:7264");
var client = new ScheduleGrpc.ScheduleGrpcClient(channel);

var schedules = client.GetAllAsync(new EmptyRequest());
Console.WriteLine("Schedules: ");
foreach (var item in schedules.Items)
{
    Console.WriteLine($"ID: {item.Id}");
    Console.WriteLine($"UserID: {item.UserId}");
    Console.WriteLine($"WorkDate: {item.WorkDate}");
    Console.WriteLine($"Status: {item.Status}");
    Console.WriteLine($"CreatedAt: {item.CreatedAt}");
    Console.WriteLine($"UpdatedAt: {item.UpdatedAt}");
    Console.WriteLine($"Title: {item.Title}");
    Console.WriteLine($"Description: {item.Description}");
    Console.WriteLine($"Location: {item.Location}");
    Console.WriteLine($"Notes: {item.Notes}");
    Console.WriteLine();
}

Console.WriteLine("Find by Id.");
var getById = Console.ReadLine();
if (!string.IsNullOrEmpty(getById))
{
    var idRequest = new IdRequest { Id = getById };
    var schedule = client.GetByIdAsync(idRequest);
    Console.WriteLine($"ID: {schedule.Id}");
    Console.WriteLine($"UserID: {schedule.UserId}");
    Console.WriteLine($"WorkDate: {schedule.WorkDate}");
    Console.WriteLine($"Status: {schedule.Status}");
    Console.WriteLine($"CreatedAt: {schedule.CreatedAt}");
    Console.WriteLine($"UpdatedAt: {schedule.UpdatedAt}");
    Console.WriteLine($"Title: {schedule.Title}");
    Console.WriteLine($"Description: {schedule.Description}");
    Console.WriteLine($"Location: {schedule.Location}");
    Console.WriteLine($"Notes: {schedule.Notes}");
    Console.WriteLine();
}

Console.WriteLine("Create a new schedule.");
var newId = Console.ReadLine();
var newUserId = Console.ReadLine();
var newWorkDate = Console.ReadLine();
var newStatus = Console.ReadLine();
var newCreatedAt = Console.ReadLine();
var newUpdatedAt = Console.ReadLine();
var newTitle = Console.ReadLine();
var newDescription = Console.ReadLine();
var newLocation = Console.ReadLine();
var newNotes = Console.ReadLine();
var newSchedule = new Item
{
    Id = newId,
    UserId = newUserId,
    WorkDate = newWorkDate,
    Status = newStatus,
    CreatedAt = newCreatedAt,
    UpdatedAt = newUpdatedAt,
    Title = newTitle,
    Description = newDescription,
    Location = newLocation,
    Notes = newNotes
};
var result = client.CreateAsync(newSchedule);
Console.WriteLine($"Create result: {result.Status}");
Console.WriteLine($"Message: {result.Message}");
Console.WriteLine();

Console.WriteLine("Update a schedule.");
var updateId = Console.ReadLine();
var updateUserId = Console.ReadLine();
var updateWorkDate = Console.ReadLine();
var updateStatus = Console.ReadLine();
var updateCreatedAt = Console.ReadLine();
var updateUpdatedAt = Console.ReadLine();
var updateTitle = Console.ReadLine();
var updateDescription = Console.ReadLine();
var updateLocation = Console.ReadLine();
var updateNotes = Console.ReadLine();
var updateSchedule = new Item
{
    Id = updateId,
    UserId = updateUserId,
    WorkDate = updateWorkDate,
    Status = updateStatus,
    CreatedAt = updateCreatedAt,
    UpdatedAt = updateUpdatedAt,
    Title = updateTitle,
    Description = updateDescription,
    Location = updateLocation,
    Notes = updateNotes
};
var updateResult = client.UpdateAsync(updateSchedule);
Console.WriteLine($"Update result: {updateResult.Status}");
Console.WriteLine($"Message: {updateResult.Message}");
Console.WriteLine();

Console.WriteLine("Delete a schedule.");
var deleteId = Console.ReadLine();
var deleteRequest = new IdRequest { Id = deleteId };
var deleteResult = client.RemoveAsync(deleteRequest);
Console.WriteLine($"Delete result: {deleteResult.Status}");
Console.WriteLine($"Message: {deleteResult.Message}");
Console.WriteLine();

Console.WriteLine("Press any key to exit...");
Console.ReadKey();