using System;
using System.Text;
using System.Threading.Tasks;
using Azure;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using SCBS.Repositories.Models;

namespace SCBS.MVCWebApp.Controllers
{
    public class SchedulesController : Controller
    {
        private string APIEndPoint = "https://localhost:7067/graphql";

        public SchedulesController() { }

        // GET: Schedules
        public async Task<IActionResult> Index()
        {
            using (var httpClient = new HttpClient())
            {
                var query = new
                {
                    query = @"
                    {
                        allSchedules {
                            id
                            userId
                            workDate
                            status
                            createdAt
                            updatedAt
                            title
                            description
                            location
                            notes
                            user {
                                id
                                username
                                fullName
                                password
                                email
                                avatar
                                phone
                                gender
                                role
                                status
                                rating
                                createdAt
                                updatedAt
                            }
                        }
                    }"
                };
                var jsonContent = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");

                using (var response = await httpClient.PostAsync(APIEndPoint, jsonContent))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var jsonData = JsonConvert.DeserializeObject<JObject>(content);

                        // Extract "schedules" field and convert it to a list
                        var schedulesJson = jsonData["data"]?["allSchedules"];

                        if (schedulesJson != null)
                        {
                            var result = schedulesJson.ToObject<List<Schedule>>();
                            return View(result);
                        }
                    }
                }
            }
            return View(new List<Schedule>());
        }

        // GET: Schedules/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            using (var httpClient = new HttpClient())
            {
                var query = new
                {
                    query = $@"
                    {{
                        byIdSchedule(id: ""{id}"") {{
                            id
                            userId
                            workDate
                            status
                            createdAt
                            updatedAt
                            title
                            description
                            location
                            notes
                            user {{
                                id
                                username
                                fullName
                                password
                                email
                                avatar
                                phone
                                gender
                                role
                                status
                                rating
                                createdAt
                                updatedAt
                            }}
                        }}
                    }}"
                };
                var jsonContent = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");

                using (var response = await httpClient.PostAsync(APIEndPoint, jsonContent))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var jsonData = JsonConvert.DeserializeObject<JObject>(content);

                        // Extract "schedules" field and convert it to a list
                        var scheduleJson = jsonData["data"]?["byIdSchedule"];

                        if (scheduleJson != null)
                        {
                            var result = scheduleJson.ToObject<Schedule>();
                            return View(result);
                        }
                    }
                }
            }
            return NotFound();
        }
        private async Task<List<User>> GetUsers()
        {
            var users = new List<User>();
            using (var httpClient = new HttpClient())
            {
                var query = new
                {
                    query = $@"
                    {{
                        allUsers {{
                            id
                            username
                            fullName
                            password
                            email
                            avatar
                            phone
                            gender
                            role
                            status
                            rating
                            createdAt
                            updatedAt
                        }}
                    }}"
                };
                var jsonContent = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");

                using (var response = await httpClient.PostAsync(APIEndPoint, jsonContent))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var jsonData = JsonConvert.DeserializeObject<JObject>(content);

                        // Extract "schedules" field and convert it to a list
                        var usersJson = jsonData["data"]?["allUsers"];

                        if (usersJson != null)
                        {
                            users = usersJson.ToObject<List<User>>();
                        }
                    }
                }
            }
            return users;
        }
        private async Task<User> GetUser(Guid? id)
        {
            var user = new User();
            using (var httpClient = new HttpClient())
            {
                var query = new
                {
                    query = $@"
                    {{
                        byIdUser(id: {id}) {{
                            id
                            username
                            fullName
                            password
                            email
                            avatar
                            phone
                            gender
                            role
                            status
                            rating
                            createdAt
                            updatedAt
                        }}
                    }}"
                };
                var jsonContent = new StringContent(JsonConvert.SerializeObject(query), Encoding.UTF8, "application/json");

                using (var response = await httpClient.PostAsync(APIEndPoint, jsonContent))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var jsonData = JsonConvert.DeserializeObject<JObject>(content);

                        // Extract "schedules" field and convert it to a list
                        var userJson = jsonData["data"]?["byIdUser"];

                        if (userJson != null)
                        {
                            user = userJson.ToObject<User>();
                        }
                    }
                }
            }
            return user;
        }
        // GET: Schedules/Create
        public async Task<IActionResult> Create()
        {
            ViewData["UserId"] = new SelectList(await GetUsers(), "Id", "FullName");
            return View();
        }

        //// POST: Schedules/Create
        //// To protect from overposting attacks, enable the specific properties you want to bind to.
        //// For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        //[HttpPost]
        //[ValidateAntiForgeryToken]
        //public async Task<IActionResult> Create([Bind("Id,UserId,WorkDate,Status,CreatedAt,UpdatedAt,Title,Description,Location,Notes")] Schedule schedule)
        //{
        //    if (ModelState.IsValid)
        //    {
               
        //    }
        //    ViewData["UserId"] = new SelectList(await GetUsers(), "Id", "FullName", schedule.UserId);
        //    return View(schedule);
        //}

        //// GET: Schedules/Edit/5
        //public async Task<IActionResult> Edit(Guid? id)
        //{
        //    if (id == null)
        //    {
        //        return NotFound();
        //    }

        //    var schedule = await _context.Schedules.FindAsync(id);
        //    if (schedule == null)
        //    {
        //        return NotFound();
        //    }
        //    ViewData["UserId"] = new SelectList(_context.Users, "Id", "Email", schedule.UserId);
        //    return View(schedule);
        //}

        //// POST: Schedules/Edit/5
        //// To protect from overposting attacks, enable the specific properties you want to bind to.
        //// For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        //[HttpPost]
        //[ValidateAntiForgeryToken]
        //public async Task<IActionResult> Edit(Guid id, [Bind("Id,UserId,WorkDate,Status,CreatedAt,UpdatedAt,Title,Description,Location,Notes")] Schedule schedule)
        //{
        //    if (id != schedule.Id)
        //    {
        //        return NotFound();
        //    }

        //    if (ModelState.IsValid)
        //    {
        //        try
        //        {
        //            _context.Update(schedule);
        //            await _context.SaveChangesAsync();
        //        }
        //        catch (DbUpdateConcurrencyException)
        //        {
        //            if (!ScheduleExists(schedule.Id))
        //            {
        //                return NotFound();
        //            }
        //            else
        //            {
        //                throw;
        //            }
        //        }
        //        return RedirectToAction(nameof(Index));
        //    }
        //    ViewData["UserId"] = new SelectList(_context.Users, "Id", "Email", schedule.UserId);
        //    return View(schedule);
        //}

        //// GET: Schedules/Delete/5
        //public async Task<IActionResult> Delete(Guid? id)
        //{
        //    if (id == null)
        //    {
        //        return NotFound();
        //    }

        //    var schedule = await _context.Schedules
        //        .Include(s => s.User)
        //        .FirstOrDefaultAsync(m => m.Id == id);
        //    if (schedule == null)
        //    {
        //        return NotFound();
        //    }

        //    return View(schedule);
        //}

        //// POST: Schedules/Delete/5
        //[HttpPost, ActionName("Delete")]
        //[ValidateAntiForgeryToken]
        //public async Task<IActionResult> DeleteConfirmed(Guid id)
        //{
        //    var schedule = await _context.Schedules.FindAsync(id);
        //    if (schedule != null)
        //    {
        //        _context.Schedules.Remove(schedule);
        //    }

        //    await _context.SaveChangesAsync();
        //    return RedirectToAction(nameof(Index));
        //}

        //private bool ScheduleExists(Guid id)
        //{
        //    return _context.Schedules.Any(e => e.Id == id);
        //}
    }
}
