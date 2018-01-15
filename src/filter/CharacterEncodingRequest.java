package filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;

public class CharacterEncodingRequest extends HttpServletRequestWrapper {

    private HttpServletRequest request;

    public CharacterEncodingRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if(value == null){
            return null;
        }
        String method = request.getMethod();
        if("Get".equalsIgnoreCase(method)){
            try {
                value = new String(value.getBytes("ISO-8859-1"), request.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String method = request.getMethod();
        String[] values = null;

        try {
            values = request.getParameterValues(name);
            if("Get".equalsIgnoreCase(method)){
                int i = 0;
                for (String str : values){
                    values[i++] = new String(str.getBytes("ISO-8859-1"), request.getCharacterEncoding());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return values;
    }
}
