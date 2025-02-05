/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectorium.crud.services;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import proyectorium.crud.entities.UserEntity;
import proyectorium.crud.exceptions.DeleteException;
import proyectorium.crud.exceptions.IncorrectCredentialsException;
import proyectorium.crud.exceptions.ReadException;
import proyectorium.crud.exceptions.UpdateException;
import proyectorium.crud.exceptions.UserAlreadyExistException;
import proyectorium.crud.exceptions.UserDoesntExistException;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("proyectorium.crud.entities.userentity")
public class UserEntityFacadeREST extends AbstractFacade<UserEntity> {

    @PersistenceContext(unitName = "CRUDProyectoriumPU")
    private EntityManager em;

    private PrivateKey privateKey;

    public UserEntityFacadeREST() {
        super(UserEntity.class);

    }

    private PrivateKey loadPrivateKey() throws Exception {
        byte[] privateKeyBytes;
        try (InputStream keyInputStream = UserEntityFacadeREST.class.getResourceAsStream("Private.key");
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            if (keyInputStream == null) {
                throw new FileNotFoundException("No se encontró el archivo de clave privada.");
            }
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = keyInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            privateKeyBytes = baos.toByteArray();
        }

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    // Método para desencriptar la contraseña recibida
    private String decryptPassword(String encryptedPasswordBase64) throws Exception {
        // Cargar la clave privada
        PrivateKey privateKey = loadPrivateKey();

        // Decodificar la contraseña cifrada de Base64
        byte[] encryptedPassword = java.util.Base64.getDecoder().decode(encryptedPasswordBase64);

        // Desencriptar la contraseña
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedPassword = cipher.doFinal(encryptedPassword);

        // Devolver la contraseña en texto plano
        return new String(decryptedPassword);
    }

    // Método para registrar un nuevo usuario
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(UserEntity entity) {
        try {
            // Cifrar la contraseña recibida desde el cliente
            String decryptedPassword = decryptPassword(entity.getPassword()); // Suponiendo que el password cifrado se pasa como byte[]

            // Hashear la contraseña descifrada
            String hashedPassword = hashPassword(decryptedPassword); // Usamos el método hashPassword del ejemplo anterior
            entity.setPassword(hashedPassword); // Establecer la contraseña hasheada

            super.create(entity);  // Guardar el usuario con la contraseña hasheada
        } catch (Exception ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, UserEntity entity) {
        try {
            //hasheo
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws UserDoesntExistException, IncorrectCredentialsException {
        try {
            try {
                super.remove(super.find(id));
            } catch (DeleteException ex) {
                Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ReadException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public UserEntity find(@PathParam("id") Long id) {
        try {
            //hasheo
            return super.find(id);
        } catch (ReadException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserEntity> findAll() {
        try {
            //hasheo
            return super.findAll();
        } catch (ReadException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        try {
            return String.valueOf(super.count());
        } catch (ReadException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Método para iniciar sesión y verificar la contraseña
    @POST
    @Path("signIn")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public UserEntity signIn(UserEntity credentials) throws IncorrectCredentialsException, UserDoesntExistException {
        try {
            // Busca al usuario por email
            UserEntity user = em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", credentials.getEmail())
                    .getSingleResult();

            // Verificar si las contraseñas coinciden (en este caso, hasheamos la contraseña entrante)
            String decryptedPassword = decryptPassword(credentials.getPassword());
            if (!verifyPassword(decryptedPassword, user.getPassword())) { // Usamos el método verifyPassword
                throw new IncorrectCredentialsException("Incorrect password.");
            }

            return user;
        } catch (javax.persistence.NoResultException e) {
            throw new UserDoesntExistException("User with email " + credentials.getEmail() + " does not exist.");
        } catch (Exception e) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, "Error en signIn: ", e);
            throw new RuntimeException("An error occurred during sign-in: " + e.getMessage());
        }

    }

    // Método para obtener la clave pública
    @GET
    @Path("getPublicKey")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPublicKey() {
        try {
            // Leer la clave pública desde el archivo
            byte[] publicKeyBytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("C://Users/2dam/Desktop/Proyectorium-ClientApp/src/Public.key"));
            return Response.status(Response.Status.OK).entity(new String(publicKeyBytes)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al leer la clave pública").build();
        }
    }

    private String hashPassword(String password) throws Exception {
        // Usar un salt único para añadir seguridad
        String salt = "randomSalt";  // Asegúrate de que este salt sea único para cada usuario

        // Concatenamos el salt con la contraseña para incrementar la seguridad
        String saltedPassword = password + salt;

        // Obtener el algoritmo MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        // Hashear la contraseña concatenada con el salt
        byte[] hashedBytes = md.digest(saltedPassword.getBytes());

        // Codificar el resultado en base64 para que sea legible y almacenable
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    // Método para verificar la contraseña
    private boolean verifyPassword(String enteredPassword, String storedPasswordHash) throws Exception {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        return storedPasswordHash.equals(hashedEnteredPassword);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
