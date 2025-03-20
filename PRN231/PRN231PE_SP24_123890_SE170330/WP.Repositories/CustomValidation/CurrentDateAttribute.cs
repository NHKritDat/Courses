using System.ComponentModel.DataAnnotations;

namespace WP.Repositories.CustomValidation
{
    [AttributeUsage(AttributeTargets.Property, AllowMultiple = false)]
    public class CurrentDateAttribute : ValidationAttribute
    {
        public CurrentDateAttribute()
        {
            ErrorMessage = "CreatedDate = current date.";
        }

        public override bool IsValid(object value)
        {
            if (value is DateTime dateValue)
            {
                return dateValue.Date == DateTime.Today;
            }

            return false;
        }
    }
}
