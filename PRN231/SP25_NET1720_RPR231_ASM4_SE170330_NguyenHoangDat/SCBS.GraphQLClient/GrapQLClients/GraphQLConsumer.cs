using GraphQL;
using GraphQL.Client.Http;
using GraphQL.Client.Serializer.Newtonsoft;
using SCBS.GraphQLClient.Models;

namespace SCBS.GraphQLClient.GrapQLClients
{
    public class GraphQLConsumer
    {
        private static string APIEndPoint = "https://localhost:7067/graphql/";
        private readonly GraphQLHttpClient _graphqlClient = new GraphQLHttpClient(APIEndPoint, new NewtonsoftJsonSerializer());
        public async Task<List<Schedule>> GetSchedules()
        {
            try
            {
                #region GraphQL Request

                var graphQLRequest = new GraphQLRequest
                {
                    //// Get Query String from Postman
                    Query = @"
                        query AllSchedules {
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
                        }   
                    ",
                    OperationName = "AllSchedules"
                };
                #endregion

                var response = await _graphqlClient.SendQueryAsync<SchedulesGraphQLResponse>(graphQLRequest);
                var result = response?.Data?.AllSchedules;

                return result;
            }
            catch (Exception ex)
            {
                return new List<Schedule>();
            }
        }
        public async Task<Schedule> GetSchedule(Guid id)
        {
            try
            {
                #region GraphQL Request

                var graphQLRequest = new GraphQLRequest
                {
                    //// Get Query String from Postman
                    Query = $@"
                        query ByIdSchedule {{
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
                        }}
                    ",
                    OperationName = "ByIdSchedule"
                };
                #endregion

                var response = await _graphqlClient.SendQueryAsync<ScheduleGraphQLResponse>(graphQLRequest);
                var result = response?.Data?.ByIdSchedule;

                return result;
            }
            catch (Exception ex)
            {
                return new Schedule();
            }
        }
        public async Task<int> Create(Schedule schedule)
        {
            try
            {
                #region GraphQL Request

                var graphQLRequest = new GraphQLRequest
                {
                    //// Get Query String from Postman
                    Query = $@"
                        mutation CreateSchedule {{
                            createSchedule(
                                item: {{
                                    id: ""{schedule.Id}""
                                    userId: ""{schedule.UserId}""
                                    workDate: ""{schedule.WorkDate.ToString("yyyy-MM-ddTHH:mm:ss.fffZ")}""
                                    status: ""{schedule.Status}""
                                    title: {(string.IsNullOrEmpty(schedule.Title) ? "null" : $@"""{schedule.Title}""")}
                                    description: {(string.IsNullOrEmpty(schedule.Description) ? "null" : $@"""{schedule.Description}""")}
                                    location: {(string.IsNullOrEmpty(schedule.Location) ? "null" : $@"""{schedule.Location}""")}
                                    notes: {(string.IsNullOrEmpty(schedule.Notes) ? "null" : $@"""{schedule.Notes}""")}
                                }}
                            )
                        }}
                    ",
                    OperationName = "CreateSchedule"
                };
                #endregion

                var response = await _graphqlClient.SendMutationAsync<CreateScheduleGraphQLResponse>(graphQLRequest);
                var result = response?.Data?.CreateSchedule;

                return result ?? 0;
            }
            catch
            {
                return 0;
            }
        }

        public async Task<int> Update(Schedule schedule)
        {
            try
            {
                #region GraphQL Request
                var graphQLRequest = new GraphQLRequest
                {
                    //// Get Query String from Postman
                    Query = $@"
                        mutation UpdateSchedule {{
                            updateSchedule(
                                item: {{
                                    id: ""{schedule.Id}""
                                    userId: ""{schedule.UserId}""
                                    workDate: ""{schedule.WorkDate.ToString("yyyy-MM-ddTHH:mm:ss.fffZ")}""
                                    status: ""{schedule.Status}""
                                    title: {(string.IsNullOrEmpty(schedule.Title) ? "null" : $@"""{schedule.Title}""")}
                                    description: {(string.IsNullOrEmpty(schedule.Description) ? "null" : $@"""{schedule.Description}""")}
                                    location: {(string.IsNullOrEmpty(schedule.Location) ? "null" : $@"""{schedule.Location}""")}
                                    notes: {(string.IsNullOrEmpty(schedule.Notes) ? "null" : $@"""{schedule.Notes}""")}
                                }}
                            )
                        }}
                    ",
                    OperationName = "UpdateSchedule"
                };
                #endregion
                var response = await _graphqlClient.SendMutationAsync<UpdateScheduleGraphQLResponse>(graphQLRequest);
                var result = response?.Data?.UpdateSchedule;
                return result ?? 0;
            }
            catch
            {
                return 0;
            }
        }

        public async Task<bool> Delete(Guid id)
        {
            try
            {
                #region GraphQL Request
                var graphQLRequest = new GraphQLRequest
                {
                    //// Get Query String from Postman
                    Query = $@"
                        mutation RemoveSchedule {{
                            removeSchedule(id: ""{id}"")
                        }}
                    ",
                    OperationName = "RemoveSchedule"
                };
                #endregion
                var response = await _graphqlClient.SendMutationAsync<DeleteScheduleGraphQLResponse>(graphQLRequest);
                var result = response?.Data?.DeleteSchedule;
                return result ?? false;
            }
            catch
            {
                return false;
            }
        }
        public async Task<List<User>> GetUsers()
        {
            try
            {
                #region GraphQL Request
                var graphQLRequest = new GraphQLRequest
                {
                    //// Get Query String from Postman
                    Query = @"
                        query AllUsers {
                            allUsers {
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
                    ",
                    OperationName = "AllUsers"
                };
                #endregion
                var response = await _graphqlClient.SendQueryAsync<UsersGraphQLResponse>(graphQLRequest);
                var result = response?.Data?.AllUsers;
                return result;
            }
            catch
            {
                return new List<User>();
            }
        }
        public async Task<User> GetUser(Guid id)
        {
            try
            {
                #region GraphQL Request
                var graphQLRequest = new GraphQLRequest
                {
                    //// Get Query String from Postman
                    Query = $@"
                        query ByIdUser {{
                            byIdUser(id: ""{id}"") {{
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
                    ",
                    OperationName = "ByIdUser"
                };
                #endregion
                var response = await _graphqlClient.SendQueryAsync<UserGraphQLResponse>(graphQLRequest);
                var result = response?.Data?.ByIdUser;
                return result;
            }
            catch
            {
                return new User();
            }
        }
    }
}
