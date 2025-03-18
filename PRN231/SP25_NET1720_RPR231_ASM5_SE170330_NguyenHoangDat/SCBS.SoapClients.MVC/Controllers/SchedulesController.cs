using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using SCBS.SoapClients.MVC.SoapClients;
using SCBSWCFServiceReference;

namespace SCBS.SoapClients.MVC.Controllers
{
    public class SchedulesController : Controller
    {
        private readonly SoapConsumer _soapConsumer;
        public SchedulesController(SoapConsumer soapConsumer)
        {
            _soapConsumer = soapConsumer;
        }
        // GET: Schedules
        public async Task<IActionResult> Index()
        {
            try
            {
                var result = await _soapConsumer.GetSchedules();
                return View(result);
            }
            catch (Exception ex)
            {

            }
            return View(new List<Schedule>());
        }

        // GET: Schedules/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            try
            {
                var result = await _soapConsumer.GetSchedule(id);
                if (result != null)
                {
                    return View(result);
                }
            }
            catch (Exception ex)
            {
            }

            return NotFound();
        }
        private async Task<List<User>> GetUsers()
        {
            var users = new List<User>();
            try
            {
                users = await _soapConsumer.GetUsers();
            }
            catch (Exception ex)
            {
            }
            return users;
        }
        // GET: Schedules/Create
        public async Task<IActionResult> Create()
        {
            ViewData["UserId"] = new SelectList(await GetUsers(), "Id", "Username");
            return View();
        }

        // POST: Schedules/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,UserId,WorkDate,Status,CreatedAt,UpdatedAt,Title,Description,Location,Notes")] Schedule schedule)
        {
            if (ModelState.IsValid)
            {
                try
                {
                    var result = await _soapConsumer.CreateSchedule(schedule);
                    if (result > 0)
                    {
                        return RedirectToAction(nameof(Index));
                    }
                }
                catch (Exception ex)
                {
                }
            }
            ViewData["UserId"] = new SelectList(await GetUsers(), "Id", "Username", schedule.UserId);
            return View(schedule);
        }

        // GET: Schedules/Edit/5
        public async Task<IActionResult> Edit(string? id)
        {
            if (id == null)
            {
                return NotFound();
            }
            var Users = await GetUsers();
            try
            {
                var result = await _soapConsumer.GetSchedule(id);
                if (result != null)
                {
                    ViewData["UserId"] = new SelectList(Users, "Id", "Username", result.UserId);
                    return View(result);
                }
            }
            catch (Exception ex)
            {
            }
            return NotFound();
        }

        // POST: Schedules/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid id, [Bind("Id,UserId,WorkDate,Status,CreatedAt,UpdatedAt,Title,Description,Location,Notes")] Schedule schedule)
        {
            if (id != schedule.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    var result = await _soapConsumer.UpdateSchedule(schedule);
                    if (result > 0)
                    {
                        return RedirectToAction(nameof(Index));
                    }
                }
                catch (Exception ex)
                {
                }
            }
            ViewData["UserId"] = new SelectList(await GetUsers(), "UserId", "Username", schedule.UserId);
            return View(schedule);
        }

        // GET: Schedules/Delete/5
        public async Task<IActionResult> Delete(string? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            try
            {
                var result = await _soapConsumer.GetSchedule(id);
                if (result != null)
                {
                    return View(result);
                }
            }
            catch (Exception ex)
            {
            }
            return NotFound();
        }

        // POST: Schedules/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            try
            {
                var result = await _soapConsumer.DeleteSchedule(id);
                if (result)
                {
                    return RedirectToAction(nameof(Index));
                }
            }
            catch (Exception ex)
            {
            }
            return RedirectToAction(nameof(Index));
        }
    }
}
