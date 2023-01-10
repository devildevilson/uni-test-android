package com.example.hello_world

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.StandardCharsets

object security {
  fun is_file_exists(ctx: Context, file_name: String) : Boolean {
    val file = File(ctx.filesDir, file_name)
    return file.exists()
  }

  fun create_file(ctx: Context, file_name: String, content: String) {
    // this is equivalent to using deprecated MasterKeys.AES256_GCM_SPEC
    val spec = KeyGenParameterSpec.Builder(
      MasterKey.DEFAULT_MASTER_KEY_ALIAS,
      KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
      .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
      .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
      .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
      .build()

    val master_key = MasterKey.Builder(ctx)
      .setKeyGenParameterSpec(spec)
      .build()

    // Create a file with this name or replace an entire existing file
    // that has the same name. Note that you cannot append to an existing file,
    // and the filename cannot contain path separators.
    val encrypted_file_write = EncryptedFile.Builder(
      ctx,
      File(ctx.filesDir, file_name),
      master_key,
      EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build()

    // тут видимо зададим данные сессии, что там должно быть?
    // наверное в виде username=... , token=...
    // загрузим оттуда данные при старте приложения
    val file_content = content.toByteArray(StandardCharsets.UTF_8)
    encrypted_file_write.openFileOutput().apply {
      write(file_content)
      flush()
      close()
    }
  }

  fun load_file_content(ctx: Context, file_name: String) : String {
    if (!is_file_exists(ctx, file_name)) return ""

    // this is equivalent to using deprecated MasterKeys.AES256_GCM_SPEC
    val spec = KeyGenParameterSpec.Builder(
      MasterKey.DEFAULT_MASTER_KEY_ALIAS,
      KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
      .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
      .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
      .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
      .build()

    val master_key = MasterKey.Builder(ctx)
      .setKeyGenParameterSpec(spec)
      .build()

    val encrypted_file_read = EncryptedFile.Builder(
      ctx,
      File(ctx.filesDir, file_name),
      master_key,
      EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build()

    val inputStream = encrypted_file_read.openFileInput()
    val byteArrayOutputStream = ByteArrayOutputStream()
    var next_byte: Int = inputStream.read()
    while (next_byte != -1) {
      byteArrayOutputStream.write(next_byte)
      next_byte = inputStream.read()
    }

    return byteArrayOutputStream.toString("UTF-8")
  }
}