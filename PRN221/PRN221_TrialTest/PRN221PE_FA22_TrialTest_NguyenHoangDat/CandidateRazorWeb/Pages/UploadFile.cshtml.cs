using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using System.ComponentModel.DataAnnotations;

namespace CandidateRazorWeb.Pages
{
    public class UploadFileModel : PageModel
    {
        private IHostEnvironment _environment;
        public UploadFileModel(IHostEnvironment environment)
        {
            _environment = environment;
        }
        [Required(ErrorMessage = "Please choose at least one file.")]
        [DataType(DataType.Upload)]
        [FileExtensions(Extensions = "png,jpg,jpeg,gif")]
        [Display(Name = "Choose file(s) to upload")]
        [BindProperty]
        public IFormFile[] FileUploads { get; set; }
        public async Task OnPostAsync()
        {
            if (FileUploads != null)
            {
                foreach (var FileUpload in FileUploads)
                {
                    var file = Path.Combine(_environment.ContentRootPath, "Images", FileUpload.FileName);
                    using (var fileStream = new FileStream(file, FileMode.Create))
                        await FileUpload.CopyToAsync(fileStream);
                }
            }
        }
    }
}
