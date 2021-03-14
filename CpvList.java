package ws;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import java.util.Properties;

import javax.naming.InitialContext;

import javax.sql.DataSource;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@Path("Cpv")
@Consumes("application/json")
@Produces("application/json")
public class CpvList {
    public CpvList() {
        super();
    }
    @Path("Cpv/GetByCpvCode")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String getByCpvCode(InputStream incomingData,@HeaderParam("key") String key) {
            Connection conn = null;
            conn = getOracleConnection();
            if (conn != null) {
                String code= null;
                               String selectTableSQL = "";
                               List<JSONObject> res = new ArrayList<JSONObject>();
                               JSONObject obj22 = null;
                               try {
                                   obj22 = parseJasonObject(incomingData);
                                   System.out.println(obj22.get("code").toString());
                               } catch (Exception e) {
                                   JSONObject obj = new JSONObject();
                                   obj.put("code", "201");
                                   obj.put("message", "Error IN Your Data.");
                                   if (conn != null) {
                                       try {
                                           conn.close();
                                       } catch (SQLException f) {
                                       }
                                   }
                                   return obj.toString();
                               }
                               if (checkKey(key)) {
                                   try {
                                      if(obj22.get("code")!=null)
                                           code = obj22.get("code").toString();
                                   } catch (Exception e) {
                                       e.printStackTrace();
                                       JSONObject obj = new JSONObject();
                                       obj.put("code", "201");
                                       obj.put("message", "Error IN Your Data.");
                                       if (conn != null) {
                                           try {
                                               conn.close();
                                           } catch (SQLException f) {
                                           }
                                       }
                                       return obj.toString();
                                   }
                    try {
                        selectTableSQL ="select cpv_code, description_EN from CPV where cpv_code=?";
                        PreparedStatement ps = conn.prepareStatement(selectTableSQL);
                        ps.setString(1,code);
                        ResultSet rs = ps.executeQuery();
                        System.out.println(selectTableSQL);
                        while (rs.next()) {
                            JSONObject main = new JSONObject();
                            main.put("Erorr", "False");
                            JSONObject obj = new JSONObject();
                            obj.put("CPV_Code", rs.getString("cpv_code"));
                            obj.put("Description_EN", rs.getString("description_EN"));
                            obj.put("IsValid","Valid Code");
                            res.add(obj);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        JSONObject obj = new JSONObject();
                        obj.put("code", "202");
                        obj.put("message", "Database Connection Error ...");
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException f) {
                            }
                        }
                        return obj.toString();
                    }

                    if (res.size() == 0) {
                        JSONObject obj = new JSONObject();
                        obj.put("code", "203");
                        obj.put("message", "Invalid CPV-Code!");
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException f) {
                            }
                        }
                        return obj.toString();
                    } else {
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException f) {
                            }
                        }
                        return res.toString();
                    }
                } else {
                    JSONObject obj = new JSONObject();
                    obj.put("code", "401");
                    obj.put("message", "Error Unauthorized .");
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException f) {
                        }
                    }
                    return obj.toString();
                }
            } else {
                JSONObject obj = new JSONObject();
                obj.put("code", "201");
                obj.put("message", "Error IN Database Connection .");
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException f) {
                    }
                }
                return obj.toString();
            }
        }
    
            @Path("Cpv/GetByCpvDivGrpClsCtg")
            @POST
            @Consumes("application/json")
            @Produces("application/json")
            public String getByCpvDivGrpClsCtg(InputStream incomingData,@HeaderParam("key") String key) {
                    Connection conn = null;
                    conn = getOracleConnection();
                    if (conn != null) {
                        String cpvCode= null;
                                       String selectTableSQL = "";
                                       List<JSONObject> res = new ArrayList<JSONObject>();
                                       JSONObject obj22 = null;
                                       try {
                                           obj22 = parseJasonObject(incomingData);
                                       } catch (Exception e) {
                                           JSONObject obj = new JSONObject();
                                           obj.put("code", "201");
                                           obj.put("message", "Error IN Your Data.");
                                           if (conn != null) {
                                               try {
                                                   conn.close();
                                               } catch (SQLException f) {
                                               }
                                           }
                                           return obj.toString();
                                       }
                                       if (checkKey(key)) {
                                           try {
                                              if(obj22.get("cpvCode")!=null)
                                                   cpvCode = obj22.get("cpvCode").toString();
                                           } catch (Exception e) {
                                               e.printStackTrace();
                                               JSONObject obj = new JSONObject();
                                               obj.put("code", "201");
                                               obj.put("message", "Error IN Your Data..");
                                               if (conn != null) {
                                                   try {
                                                       conn.close();
                                                   } catch (SQLException f) {
                                                   }
                                               }
                                               return obj.toString();
                                           }
                            try {
                                selectTableSQL ="select cpv_code, description_EN from CPV where cpv_code like ? || '%'";
                                PreparedStatement ps = conn.prepareStatement(selectTableSQL);
                                ps.setString(1,cpvCode);
                                ResultSet rs = ps.executeQuery();
                                System.out.println(selectTableSQL);
                                while (rs.next()) {
                                    JSONObject obj = new JSONObject();
                                    obj.put("CPV_Code", rs.getString("cpv_code"));
                                    obj.put("Description_EN", rs.getString("description_EN"));
                                    obj.put("IsValid","Valid Code");
                                    res.add(obj);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                JSONObject obj = new JSONObject();
                                obj.put("code", "202");
                                obj.put("message", "Database Connection Error ...");
                                if (conn != null) {
                                    try {
                                        conn.close();
                                    } catch (SQLException f) {
                                    }
                                }
                                return obj.toString();
                            }

                            if (res.size() == 0) {
                                JSONObject obj = new JSONObject();
                                obj.put("code", "203");
                                obj.put("message", "Invalid CPV-Code!");
                                if (conn != null) {
                                    try {
                                        conn.close();
                                    } catch (SQLException f) {
                                    }
                                }
                                return obj.toString();
                            } else {
                                if (conn != null) {
                                    try {
                                        conn.close();
                                    } catch (SQLException f) {
                                    }
                                }
                                return res.toString();
                            }
                        } else {
                            JSONObject obj = new JSONObject();
                            obj.put("code", "401");
                            obj.put("message", "Error Unauthorized .");
                            if (conn != null) {
                                try {
                                    conn.close();
                                } catch (SQLException f) {
                                }
                            }
                            return obj.toString();
                        }
                    } else {
                        JSONObject obj = new JSONObject();
                        obj.put("code", "201");
                        obj.put("message", "Error IN Database Connection .");
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException f) {
                            }
                        }
                        return obj.toString();
                    }
                }

        private boolean checkKey(String key) {
             
            if (key.equals("65344323-CPV-CODE-LIST-7e0e8c2174b6")) {
                return true;
            } else {
                return false;
            }

        }

        private Connection getOracleConnection() {
            
                   Connection conn = null;
                                             try {
                                                 String oracleDriver = "oracle.jdbc.driver.OracleDriver";
                                                 String url = "jdbc:oracle:thin:@localhost:1521:orcl19";
                                                 String userName = "raghad";
                                                 String password = "raghad";
                                                 Class.forName(oracleDriver);
                                                 conn = DriverManager.getConnection(url, userName, password);
                                                 System.out.println("#####################connnected#####################");
                                             } catch (Exception e) {
                                                 e.printStackTrace();
                                             }
                                             return conn;
                   
               }

        private JSONObject parseJasonObject(InputStream incomingData) {
            JSONObject obj22 = null;
            StringBuilder crunchifyBuilder = new StringBuilder();
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
                String line = null;
                while ((line = in.readLine()) != null) {
                    crunchifyBuilder.append(line);
                }
            } catch (Exception e) {
                System.out.println("Error Parsing: - ");
                return null;
            }
            Object obj1 = JSONValue.parse(crunchifyBuilder.toString());

            JSONArray array = new JSONArray();
            array.add(obj1);
            String ss = array.get(0).toString();
            obj22 = (JSONObject) array.get(0);
            return obj22;
        }


        }


